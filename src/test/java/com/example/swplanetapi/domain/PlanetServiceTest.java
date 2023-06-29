package com.example.swplanetapi.domain;

import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;

import static com.example.swplanetapi.common.PlanetConstants.PLANET;
import static com.example.swplanetapi.common.PlanetConstants.INVALID_PLANET;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = PlanetService.class) // Monta contexto aplicação Spring (procura por Bean)
public class PlanetServiceTest {

    //@Autowired
    @InjectMocks // Cria instancia real planetService e todas as dependencias são injetada com mock
    private PlanetService planetService;

    // @MockBean
    @Mock //
    private PlanetRepository planetRepository;

    @Test
    //nome do método (operacao_estado_retorno)
    public void createPlanet_WithValidData_ReturnsPlanet() {
        //quando for chamado com planet que é constante, então retorna o planet
        when(planetRepository.save(PLANET)).thenReturn(PLANET);


        // system under test -> normalmente a variavel de test colocamos esse nome sut
        Planet sut = planetService.create(PLANET);
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test  // teste dados invalido
    public void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test // teste id existente
    public void getPlanet_ByExisting_ReturnsPlanet(){
        when(planetRepository.findById(1l)).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.get(1l);
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);


    }


    @Test // teste id inexistente
    public void getPlanet_ByUnexistingId_ReturnsEmpty(){
        when(planetRepository.findById(1l)).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.get(1l);

        assertThat(sut).isEmpty();

    }
}
