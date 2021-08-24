package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;

public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar(Object classe) {
        return calcular(Somar.class, classe);
    }

    @Override
    public BigDecimal subtrair(Object classe) {
        return calcular(Subtrair.class, classe);
    }

    @Override
    public BigDecimal totalizar(Object classe) {
        return somar(classe).subtract(subtrair(classe));
    }
    
    private BigDecimal calcular(Class<? extends Annotation> tipo, Object classe) {
        
    	BigDecimal resultado = BigDecimal.ZERO;
        Field[] atributos = classe.getClass().getDeclaredFields();
        
        for (Field atributo : atributos) {
            if (atributo.getType().equals(BigDecimal.class) && atributo.isAnnotationPresent(tipo)) {
                try {
                    atributo.setAccessible(true);
                    resultado = resultado.add((BigDecimal) atributo.get(classe));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultado;
    }
}