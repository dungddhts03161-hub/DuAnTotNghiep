package dao;

import java.util.List;

public interface CrudDAO<Entity, ID> {
	int create(Entity entity);

	int update(Entity entity);

	int delete(ID id);

	List<Entity> findAll();

	Entity findById(ID id);

	List<Entity> findBySql(String sql, Object... value);
}

