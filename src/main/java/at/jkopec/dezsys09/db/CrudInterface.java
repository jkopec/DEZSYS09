package at.jkopec.dezsys09.db;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by jakubkopec on 06.04.16.
 */
public interface CrudInterface extends CrudRepository<User, String> {
}
