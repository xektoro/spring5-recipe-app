package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.example.spring5recipeapp.converters.UnitOfMeasureEntityToCommand;
import com.example.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureEntityToCommand unitOfMeasureEntityToCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureEntityToCommand unitOfMeasureEntityToCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureEntityToCommand = unitOfMeasureEntityToCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {

        // StreamSupport is a convenient way to swap a iterator to something that we can stream against
        // spliterator will give us Java8 stream
        // and map is used here for coverting from entity object to command object
        // and collect to set
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                        .spliterator(), false)
                    .map(unitOfMeasureEntityToCommand::convert)
                    .collect(Collectors.toSet());
    }
}
