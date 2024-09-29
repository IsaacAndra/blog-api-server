package com.isaacandrade.blog.service;

import com.isaacandrade.blog.domain.about.*;
import com.isaacandrade.blog.exception.AboutNotFoundException;
import com.isaacandrade.blog.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutService {

    @Autowired
    AboutRepository aboutRepository;


    public AboutDTO findContentById(Long id) throws AboutNotFoundException {
        About aboutById = aboutRepository.findById(id).orElseThrow(
                () -> new AboutNotFoundException("About With id " + id + " Was Not Found!")
        );

        return mapToAboutDTO(aboutById);
    }


    public AboutDTO createContent(CreateAboutDTO data) {
        About about = new About();
        about.setContent(data.content());

        if (data.content() == null) {
            throw new ConstraintViolationException("Content Cannot Be Null!");
        }

        About savedAbout = aboutRepository.save(about);

        return mapToAboutDTO(savedAbout);
    }

    public AboutDTO updateContent(Long id, EditAboutDTO data) throws AboutNotFoundException {
        About about = aboutRepository.findById(id).orElseThrow(
                () -> new AboutNotFoundException("About With Title " + id + " Was Not Found!")
        );
        about.updateAbout(data);
        aboutRepository.save(about);

        return mapToAboutDTO(about);
    }

    public void deleteAbout(Long id) throws AboutNotFoundException {
        About about = aboutRepository.findById(id).orElseThrow(
                () -> new AboutNotFoundException("About With Title " + id + " Was Not Found!")
        );

        aboutRepository.delete(about);
    }


    private AboutDTO mapToAboutDTO(About about) {
        return new AboutDTO(about.getId(), about.getContent());
    }
}
