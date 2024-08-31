package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Consultant;
import com.example.capstone3.Model.Consultation;
import com.example.capstone3.Model.Users;
import com.example.capstone3.Repository.ConsultantRepository;
import com.example.capstone3.Repository.ConsultationRepository;
import com.example.capstone3.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final ConsultantRepository consultantRepository;
    private final UsersRepository usersRepository;

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public void addNewConsultation(Consultation consultation, Integer consultant_id, Integer user_id) {
        Consultant consultant1 = consultantRepository.findConsultantById(consultant_id);
        Users users=usersRepository.findUsersById(user_id);
        if (consultant1 == null) {
            throw  new ApiException("Consultant does not exist");
        }

        if(users==null){
            throw new ApiException("Users does not exist");
        }
        double TotalAmount =consultation.getConsultationFee()*consultation.getDuration();
        consultation.setTotalAmount(TotalAmount);

        consultation.setConsultant(consultant1);
        consultation.setUsers(users);

        consultation.setCreatedAt(LocalDate.now());
        consultation.setUpdatedAt(LocalDate.now());
        consultation.setStatus("Pending");
        consultationRepository.save(consultation);
    }

    public void updateConsultation(Integer id,Consultation consultation) {
       Consultation consultation1 = consultationRepository.findConsultationById(id);

        if (consultation1 == null) {
            throw new ApiException("Consultation not found");
        }

        consultation1.setConsultationDate(consultation.getConsultationDate());
        consultation1.setTopic(consultation.getTopic());
        consultation1.setStatus(consultation.getStatus());
        consultation1.setDuration(consultation.getDuration());
        //consultation1.setRecommendation(consultation.getRecommendation());
        consultation1.setUpdatedAt(LocalDate.now());
        consultationRepository.save(consultation1);
    }


    public void deleteConsultation(Integer id) {
        Consultation consultation1 = consultationRepository.findConsultationById(id);
        if (consultation1 == null) {
            throw new ApiException("Consultation not found");
        }
       consultationRepository.delete(consultation1);
    }

  //5: Change status to complete
    public void ChangeStatusToCompleted(Integer consultationid,Integer consultatid,Integer userId){
        Consultation consultation=consultationRepository.findConsultationById(consultationid);
        Consultant consultant=consultantRepository.findConsultantById(consultatid);
        Users users=usersRepository.findUsersById(userId);
        if(consultation==null||consultant==null){
            throw new ApiException("Consultation or consultant not found");
        }
        if (!consultation.getConsultant().equals(consultant)) {
            throw new ApiException("consultant or consultation not found ");
        }
        if(consultation.getStatus().equalsIgnoreCase("Completed")){
            throw new ApiException("It is already Completed.");
        }
        if(!consultation.getStatus().equalsIgnoreCase("Scheduled")){
            throw new ApiException("must be Scheduled");
        }
        if(consultant.getConsultantConfirmed().equals(false)|| users.getClientConfirmed().equals(false)){
            throw new ApiException("The end of the session must be confirmed by the consultant and the client.");
        }
        if(consultation.getStatus().equalsIgnoreCase("Canceled")){
            throw new ApiException("The consultation is already Canceled.");
        }
        //edit
           consultant.setConsultantConfirmed(false);
           users.setClientConfirmed(false);
            consultation.setUpdatedAt(LocalDate.now());
            consultation.setStatus("Completed");
            consultationRepository.save(consultation);


    }


   public void extendConsultationDuration(Integer userId,Integer consultationId, double additionalDuration) {
    Users users=usersRepository.findUsersById(userId);
        Consultation consultation = consultationRepository.findConsultationById(consultationId);

    if (consultation == null|| users==null) {
        throw new ApiException("Consultation or user not found");
    }
    if(!consultation.getUsers().equals(users)){
        throw new ApiException("User has no consultation");
    }
    if(consultation.getStatus().equalsIgnoreCase("Completed")){
        throw new ApiException("The consultation it is already completed");
    }
    if(consultation.getStatus().equalsIgnoreCase("Canceled")){
        throw new ApiException("it is already canceled");
    }
    consultation.setDuration(consultation.getDuration() + additionalDuration);
    consultation.setTotalAmount(consultation.getDuration()*consultation.getConsultationFee());
    consultation.setUpdatedAt(LocalDate.now());

    consultationRepository.save(consultation);
}

//edit done
public void canceledStatusOfConsultation(Integer userId, Integer conId) {
    LocalDate currentDate = LocalDate.now();


    Users user = usersRepository.findUsersById(userId);
    Consultation consultation = consultationRepository.findConsultationById(conId);
    if (user == null || consultation == null) {
        throw new ApiException("User or consultation not found");
    }


    if (!user.getConsultations().contains(consultation)) {
        throw new ApiException("User has no consultation ");
    }

    if(consultation.getStatus().equalsIgnoreCase("Completed")){
        throw new ApiException("it is already Completed");
    }

    if(consultation.getStatus().equalsIgnoreCase("Canceled")){
        throw new ApiException("it is already canceled");
    }

    boolean canCancel = consultationRepository.canCancelBeforeConsultationDate(conId, currentDate);
    if (!canCancel) {
        throw new ApiException("Cannot cancel the consultation on or after the consultation date");
    }

    consultation.setStatus("Canceled");
    consultationRepository.save(consultation);
}





}
