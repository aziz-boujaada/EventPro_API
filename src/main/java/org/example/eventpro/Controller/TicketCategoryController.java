package org.example.eventpro.Controller;

import jakarta.validation.Valid;
import org.example.eventpro.Domain.TicketCategory;
import org.example.eventpro.Service.TicketCategoryService;
import org.example.eventpro.dto.CreateCategoryRequest;
import org.example.eventpro.dto.TicketCategoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class TicketCategoryController {

    private final TicketCategoryService ticketCategoryService;

    public TicketCategoryController(TicketCategoryService ticketCategoryService){
        this.ticketCategoryService = ticketCategoryService ;
    }

    // create
    @PostMapping()
    public ResponseEntity<TicketCategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request){
         return ResponseEntity.status(HttpStatus.CREATED).body(ticketCategoryService.create(request));
    }

    // get by id
    @GetMapping("/{id}")
    public TicketCategoryResponse getCategory (@PathVariable Long id){return ticketCategoryService.findById(id);}

    // get all
    @GetMapping()
    public List<TicketCategoryResponse> getAllCategories (){return ticketCategoryService.findAll();}

    // update
    @PutMapping("/{id}")
    public ResponseEntity<TicketCategoryResponse> updateCategory(
            @PathVariable Long id ,
            @Valid @RequestBody CreateCategoryRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCategoryService.update(id , request));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        ticketCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
