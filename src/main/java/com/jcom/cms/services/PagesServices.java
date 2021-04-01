package com.jcom.cms.services;

 
import com.jcom.cms.entity.Categories;
import com.jcom.cms.entity.Page;
import com.jcom.cms.repository.CategoriesRepository;
import com.jcom.cms.repository.PageRepository;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagesServices {

    @Autowired
    PageRepository pageRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    public List<Page> findByType(String type){
        return pageRepository.findByType(type);
    }

   public org.springframework.data.domain.Page<Page> findByType(String type, Pageable pageable){
       return pageRepository.findByType(type, pageable);
    }
    public org.springframework.data.domain.Page<Page> search(String search, Pageable pageable){
        return pageRepository.findByTitleContainingOrTextContaining(search, search, pageable);
    }


    public Page getOne(Integer id){
      return pageRepository.getOne(id);
  }

    public Page getPagesByCpu(String cpu){
      return pageRepository.findFirstByCpuAndType(cpu, "pages");
  }
    public Page getpost(String cpu){
        return pageRepository.findFirstByCpuAndType(cpu, "posts");
    }
    public Page getPostByCpu(String cpu){
        return pageRepository.findFirstByCpuAndType(cpu, "posts");
    }

    public Page save(Page page){
         return pageRepository.save(page);
    }

    public void delete(String id){
        pageRepository.deleteById(Integer.parseInt(id));
    }

    public long count(){
        return pageRepository.count();
    }

    public Categories getCat(String cpu){
       return categoriesRepository.findFirstByCpuContaining(cpu);
    }

     public org.springframework.data.domain.Page<Page> getPostsFromCategory(String catId, Pageable pageable){
             return pageRepository.findByTypeAndCatidContains("posts", catId, pageable);
        }

    public Page getByDirection(String direction ){
        return pageRepository.findFirstByDirection(direction);
    }

    public Iterable<Page> findAll(){
        return pageRepository.findAll();
    }

    public String testData(){
        return "test";
    }

}
