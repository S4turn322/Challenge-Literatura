package com.alura.challenge_literatura.dto;

import com.alura.challenge_literatura.model.DatosAutor;
import java.util.List;

public record LibrosDTO(Long id,
                        String titulo,
                        List<DatosAutor> autores,
                        Integer descargas,
                        List<String> idioma) {
}
