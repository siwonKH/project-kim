package com.khpt.projectkim.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khpt.projectkim.dto.ResultDto;
import com.khpt.projectkim.dto.UserPrevData;
import com.khpt.projectkim.entity.Result;
import com.khpt.projectkim.entity.User;
import com.khpt.projectkim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByStringId(String id) {
        return userRepository.findById(Long.parseLong(id)).orElseThrow();
    }


    public void setUserResults(String id, Map<String, List<Map<String, Object>>> results) {
        User user = getUserByStringId(id);

        List<Result> saveResults = new ArrayList<>();
        for (Map<String, Object> r : results.get("jobs")) {
            Result result = new Result();
            result.setCompany(r.get("company").toString());
            result.setEducation(r.get("education").toString());
            result.setRegion(r.get("location").toString());
            result.setSalary(r.get("salary").toString());
            result.setTitle(r.get("title").toString());
            result.setType(r.get("type").toString());
            result.setUrl(r.get("url").toString());
            result.setUser(user);
            saveResults.add(result);
        }

        user.setResults(saveResults);
        userRepository.save(user);
    }

    public List<ResultDto> getUserResultsAsDto(String id) {
        // Get the user
        User user = getUserByStringId(id);

        // Get the user's results
        List<Result> results = user.getResults();

        // Convert the results to a list of ResultDto
        List<ResultDto> resultDtos = results.stream().map(result -> {
            ResultDto dto = new ResultDto();
            dto.setCompany(result.getCompany());
            dto.setEducation(result.getEducation());
            dto.setRegion(result.getRegion());
            dto.setSalary(result.getSalary());
            dto.setTitle(result.getTitle());
            dto.setType(result.getType());
            dto.setUrl(result.getUrl());
            // Assuming you have a getCareer method in your Result class
            dto.setCareer(result.getCareer());
            return dto;
        }).collect(Collectors.toList());

        // Return the list of ResultDto objects
        return resultDtos;
    }

    public String getUserResultsAsString(String id) {
        try {
            // Get the user
            User user = getUserByStringId(id);

            // Get the user's results
            List<Result> results = user.getResults();

            // Convert the results to a list of maps
            List<Map<String, Object>> resultMaps = results.stream().map(result -> {
                Map<String, Object> map = new HashMap<>();
                map.put("company", result.getCompany());
                map.put("education", result.getEducation());
                map.put("location", result.getRegion());
                map.put("salary", result.getSalary());
                map.put("title", result.getTitle());
                map.put("type", result.getType());
                map.put("url", result.getUrl());
                return map;
            }).collect(Collectors.toList());

            // Create a map for the final result
            Map<String, List<Map<String, Object>>> finalResult = new HashMap<>();
            finalResult.put("jobs", resultMaps);

            // Convert the map to JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Return the JSON string
            return objectMapper.writeValueAsString(finalResult);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, List<Map<String, Object>>> getUserResultsAsMap(String id) {
        // Get the user
        User user = getUserByStringId(id);

        // Get the user's results
        List<Result> results = user.getResults();

        // Convert the results to a list of maps
        List<Map<String, Object>> resultMaps = results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("company", result.getCompany());
            map.put("education", result.getEducation());
            map.put("location", result.getRegion());
            map.put("salary", result.getSalary());
            map.put("title", result.getTitle());
            map.put("type", result.getType());
            map.put("url", result.getUrl());
            return map;
        }).collect(Collectors.toList());

        // Create a map for the final result
        Map<String, List<Map<String, Object>>> finalResult = new HashMap<>();
        finalResult.put("jobs", resultMaps);

        // Return the final map
        return finalResult;
    }



    public void setUserPrevData(String id, UserPrevData userPrevData) {
        User user = getUserByStringId(id);

        String type = userPrevData.getType();
        String region = userPrevData.getRegion();
        String education = userPrevData.getEducation();
        String career = userPrevData.getCareer();
        String category = userPrevData.getCategory();

        user.updatePrevData(type, region, education, career, category);
    }

    public UserPrevData getUserPrevData(String id) {
        User user = getUserByStringId(id);

        UserPrevData userPrevData = new UserPrevData();
        userPrevData.setType(user.getType());
        userPrevData.setRegion(user.getRegion());
        userPrevData.setEducation(user.getEducation());
        userPrevData.setCareer(user.getCareer());
        userPrevData.setCategory(user.getCategory());

        return userPrevData;
    }
}
