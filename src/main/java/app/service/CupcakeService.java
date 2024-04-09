package app.service;

import app.entities.Cupcake;
import app.exceptions.DatabaseException;
import app.persistence.CupcakeMapper;

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