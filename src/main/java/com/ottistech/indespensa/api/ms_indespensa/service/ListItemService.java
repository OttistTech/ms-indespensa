package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.ListItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.exception.UserNotFoundException;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.ListItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class ListItemService {

    private final UserRepository userRepository;
    private final ListItemRepository listItemRepository;

    public List<ListItemResponseDTO> getListItem(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        List<ListItemResponseDTO> listItemResponses = listItemRepository.findAllByUser(user);

        if (listItemResponses.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items found for the user");

        return listItemResponses;
    }
}
