package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.ToppingModel;

import java.util.List;

public interface ToppingService {
    ToppingModel getToppingByIdTopping(Long idTopping);
    List<ToppingModel> getListTopping();
}

