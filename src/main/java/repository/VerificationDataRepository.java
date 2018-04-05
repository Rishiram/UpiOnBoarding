package repository;

import model.VerificationData;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface VerificationDataRepository extends CrudRepository<VerificationData, BigInteger> {
}
