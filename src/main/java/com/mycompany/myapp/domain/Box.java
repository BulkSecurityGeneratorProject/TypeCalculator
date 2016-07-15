package com.mycompany.myapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Box {
    private Integer length=null;
    private Integer width=null;
    private Integer height=null;
    private Integer weight=null;

    public List<Integer> getSides() {
        return Arrays.asList(length,width,height).stream().filter(p -> p != null).collect(Collectors.toList());
    }

    public Integer getLongestSide() {
        return getSides().stream().max(Integer::max).orElse(0);
    }

    public Integer getShortestSide() {
        return getSides().stream().min(Integer::min).orElse(0);
    }

    public Integer getThirdSide() {
        List<Integer> values = getSides();
        checkState(values.size()==3);
        values.remove(getLongestSide());
        values.remove(getShortestSide());
        return values.get(0);
    }

}
