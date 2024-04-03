package service;

import entities.Cupcake;
import exceptions.DatabaseException;
import persistence.CupcakeMapper;

import java.util.List;

public class CupcakeService {

    private CupcakeMapper cupcakeMapper;

    public CupcakeService(CupcakeMapper cupcakeMapper) {
        this.cupcakeMapper = cupcakeMapper;
    }

    public List<Cupcake> getCupcakes() throws DatabaseException {
        return cupcakeMapper.getAllCupcakes();
    }
}
