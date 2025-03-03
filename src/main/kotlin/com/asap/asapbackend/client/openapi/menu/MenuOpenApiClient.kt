package com.asap.asapbackend.client.openapi.menu

import com.asap.asapbackend.batch.menu.MenuInfoProvider
import com.asap.asapbackend.client.openapi.menu.dto.MenuOpenApiResponse
import com.asap.asapbackend.client.openapi.vo.NeisOpenApiKey
import com.asap.asapbackend.domain.menu.domain.model.Menu
import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Flux
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class MenuOpenApiClient (
    private val neisOpenApiKey: NeisOpenApiKey,
    private val schoolRepository: SchoolRepository,
    private val objectMapper: ObjectMapper
) : MenuInfoProvider{
    override fun retrieveMenuInfo(batchSize: Int, pageNumber: Int): MenuInfoProvider.MenuDataContainer {
        val pageable = PageRequest.of(pageNumber, batchSize)
        val schools = schoolRepository.findAll(pageable)
        val hasNext = schools.hasNext()
        val menuFluxes = schools.content.map { school ->
            getMenuResponse(school)
        }
        val menuInfoList = mutableListOf<Menu>()
        Flux.merge(menuFluxes)
            .buffer(1000)
            .doOnNext(menuInfoList::addAll)
            .blockLast()
        return MenuInfoProvider.MenuDataContainer(
            menuInfo = menuInfoList,
            hasNext = hasNext
        )
    }

    private fun getMenuResponse(school: School): Flux<Menu> {
        val formatter = DateTimeFormatter.ofPattern("yyyyMM")
        val date = LocalDate.now().format(formatter)
        val apiUrl = "https://open.neis.go.kr/hub/mealServiceDietInfo"
        return WebClient.create(apiUrl).get()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .queryParam("KEY", neisOpenApiKey.key)
                    .queryParam("ATPT_OFCDC_SC_CODE", school.eduOfficeCode)
                    .queryParam("SD_SCHUL_CODE", school.schoolCode)
                    .queryParam("MLSV_YMD", date)
                    .queryParam("MMEAL_SC_CODE",2)
                    .queryParam("Type", "json")
                    .build()
            }
            .retrieve()
            .bodyToMono(String::class.java)
            .map {
                val menuOpenApiResponse = objectMapper.readValue(it, MenuOpenApiResponse::class.java)
                menuOpenApiResponse?.mealServiceDietInfo?.flatMap { menuInfo ->
                    menuInfo.row?.map {
                        it.toMenu(school)
                    } ?: emptyList()
                } ?: emptyList()
            }.flatMapMany { Flux.fromIterable(it) }
    }
}