package com.asap.asapbackend.client.openapi.menu

import com.asap.asapbackend.batch.menu.MenuInfoProvider
import com.asap.asapbackend.client.openapi.menu.dto.MenuOpenApiResponse
import com.asap.asapbackend.client.openapi.vo.NeisOpenApiKey
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
        val menuFluxex = schools.content.map { school ->
            getMenuResponse(school)
        }
        val menuInfoList = mutableListOf<MenuInfoProvider.MenuResponse>()
        Flux.merge(menuFluxex)
            .buffer(1000)
            .flatMap {
                Flux.fromIterable(it)
                    .doOnNext(menuInfoList::add)
                    .then()
            }.blockLast()
        return MenuInfoProvider.MenuDataContainer(
            menuInfo = menuInfoList,
            hasNext = hasNext
        )
    }
    private fun getMenuResponse(school: School): Flux<MenuInfoProvider.MenuResponse> {
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
                menuOpenApiResponse?.mealServiceDietInfo?.flatMap { timetableInfo ->
                    timetableInfo.row?.map {
                        it.toMenuInfo(school)
                    } ?: emptyList()
                } ?: emptyList()
            }
            .flatMapMany { Flux.fromIterable(it) }
    }

}