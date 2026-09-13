package co.edu.unbosque.metodologiaespiral.service;

import java.util.List;

import co.edu.unbosque.metodologiaespiral.entity.Usuario;

public interface CRUDOperation<D> {

   public int create(D data);

    public int deleteById(Long id);

    public List<D> getAll();

    public long count();

    public boolean exist(Long id);

    public int updateById(Long id, D newData);

	public D getById(Long id);


}
