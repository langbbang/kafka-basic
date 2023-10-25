package me.songha.kafka.basic.sample2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ping implements Serializable {
    private String msg;
    private String name;

    @Override
    public String toString() {
        return msg + ", " + name + "!";
    }
}