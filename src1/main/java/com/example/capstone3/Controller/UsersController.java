package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Plans;
import com.example.capstone3.Model.Users;
import com.example.capstone3.Service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(usersService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUsers(@Valid @RequestBody Users users){

        usersService.addUsers(users);
        return ResponseEntity.status(200).body(new ApiResponse("User added Successfully!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUsers(@PathVariable Integer id,@Valid@RequestBody Users users){

        usersService.updateUsers(id,users);
        return ResponseEntity.status(200).body(new ApiResponse("User Updated Successfully!"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUsers(@PathVariable Integer id){
        usersService.deleteUsers(id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted Successfully!"));
    }
    //many to many :assign Plan To User
    @PutMapping ("/assign/planId/userId")
    public ResponseEntity assignPlanToUser(@RequestParam Integer planId,@RequestParam  Integer userId) {
        usersService.assignPlanToUser(planId, userId);
        return ResponseEntity.status(200).body(new ApiResponse("Plan assigned Successfully to user"));
    }

    @GetMapping("/recommendation")
    public ResponseEntity getPlanRecommendation(@RequestParam String plantName) {
        Plans plan = usersService.getPlanRecommendationByPlantName(plantName);
        return ResponseEntity.status(200).body(plan);
    }

    @PutMapping("/confirmed/{userId}/{bookingId}")
    public ResponseEntity consultantConfirmed(@PathVariable Integer userId,@PathVariable Integer bookingId){
        usersService.userConfirmed(userId,bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("The session has been confirmed to be over."));
    }

    @GetMapping("/getAllNurseries")
    public ResponseEntity getAllNurseries(){
        return ResponseEntity.status(200).body(usersService.getAllNurseries());
    }

    @GetMapping("/byLocation/{location}")
    public ResponseEntity getNurseriesByLocation(@PathVariable String location) {
        return ResponseEntity.status(200).body(usersService.getNurseriesByLocation(location));
    }



}
