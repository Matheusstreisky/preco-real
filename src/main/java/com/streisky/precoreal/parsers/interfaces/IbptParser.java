package com.streisky.precoreal.parsers.interfaces;

import com.streisky.precoreal.models.Ibpt;

import java.util.List;

public interface IbptParser {

    List<Ibpt> parse(String csv, String uf);
}
