package org.example.eventpro.Service;

import org.example.eventpro.Domain.TicketCategory;
import org.example.eventpro.Exception.ResourceNotFoundException;
import org.example.eventpro.Repository.TicketCategoryRepository;
import org.example.eventpro.dto.CreateCategoryRequest;
import org.example.eventpro.dto.TicketCategoryResponse;
import org.example.eventpro.mapper.TicketCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketCategoryService {

    private final TicketCategoryRepository categoryRepository ;
    private final TicketCategoryMapper ticketCategoryMapper;

    public TicketCategoryService(TicketCategoryRepository categoryRepository , TicketCategoryMapper ticketCategoryMapper){
        this.categoryRepository = categoryRepository;
        this.ticketCategoryMapper = ticketCategoryMapper;
    }
    public TicketCategoryResponse create(CreateCategoryRequest request){

        TicketCategory category = ticketCategoryMapper.toEntity(request);
        TicketCategory savedCategory = categoryRepository.save(category);
        return ticketCategoryMapper.toResponse(savedCategory);
    }

    public TicketCategoryResponse update(Long id , CreateCategoryRequest request){

        TicketCategory category = getCategory(id);
        ticketCategoryMapper.updateEvent(request , category);
        TicketCategory savedCategory = categoryRepository.save(category);
        return ticketCategoryMapper.toResponse(savedCategory);
    }

    // find by id
    public TicketCategoryResponse findById(Long id){return ticketCategoryMapper.toResponse(getCategory(id));}


    // find all
    public List<TicketCategoryResponse> findAll(){
        return categoryRepository.findAll().stream()
                .map(ticketCategoryMapper::toResponse)
                .toList();
    }

    // delete
    public void delete(Long id){categoryRepository.delete(getCategory(id));}

    // get by id
    public TicketCategory getCategory(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found "));
    }

}
