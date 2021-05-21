package com.example.homework_v2;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Film {

    private String name;
    private String description;
    private int imageResourceId;


    public static final Film[] films = {
            new Film("Джанго освобожденный", "Эксцентричный охотник за головами, также известный как «Дантист», промышляет отстрелом самых опасных преступников на Диком Западе. Работенка пыльная, и без надежного помощника ему не обойтись. Но как найти такого и желательно не очень дорогого? Беглый раб по имени Джанго — прекрасная кандидатура. Правда, у нового помощника свои мотивы — кое с чем надо разобраться…",R.drawable.django),
            new Film("Криминальное чтиво", "Двое бандитов Винсент Вега и Джулс Винфилд проводят время в философских беседах в перерыве между разборками и «решением проблем» с должниками своего криминального босса Марселласа Уоллеса. Параллельно разворачивается три истории.",R.drawable.fiction),
            new Film("Однажды... в Голливуде", "Фильм повествует о череде событий, произошедших в Голливуде в 1969 году, на закате его «золотого века». По сюжету, известный ТВ актер Рик Далтон и его дублер Клифф Бут пытаются найти свое место в стремительно меняющемся мире киноиндустрии.",R.drawable.hollywood),
            new Film("Омерзительная восьмерка", "США после Гражданской войны. Легендарный охотник за головами Джон Рут по кличке Вешатель конвоирует заключенную. По пути к ним прибивается еще один охотник. Снежная буря вынуждает всех троих искать укрытие в лавке на отшибе, где уже расположилась весьма пестрая компания: генерал, шериф, мексиканец, француз и ковбой… И один из них — не тот, за кого себя выдает.",R.drawable.eight),
    };

    public Film(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
