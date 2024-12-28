package com.aluracursos.jokerp515.cinema.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> clazz);
}
