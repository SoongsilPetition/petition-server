package com.petition.petition.util

import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.PetitionStatus
import com.petition.petition.service.PetitionService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PetitionScheduler(
    private val petitionService: PetitionService
) {

    //매일 0시에 청원 마감일이 지난 청원들을 자동으로 거절처리
    @Scheduled(cron = "0 0 0 * * ?")
    fun petitionSchedulerMethod(){
        val petitions = petitionService.getOngoingPetitionsList()
        if (petitions != null) {
            for(petition: Petition in petitions){
                if(petition.petitionDueDate < LocalDateTime.now() && petition.status != PetitionStatus.ACCEPTED){
                    petition.status = PetitionStatus.REJECTED
                    petitionService.updatePetition(petition)
                }
            }
        }
    }
}