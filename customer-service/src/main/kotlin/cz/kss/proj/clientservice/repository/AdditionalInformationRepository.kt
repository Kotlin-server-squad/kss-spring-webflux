package cz.kss.proj.clientservice.repository

import cz.kss.proj.clientservice.entity.AdditionalInformation
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AdditionalInformationRepository: CoroutineCrudRepository<AdditionalInformation,Long> {
}