package pt.utp.estg.pp.printhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.utp.estg.pp.printhub.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByUserId(Integer userId);

    Optional<CartItem> findByUserIdAndProductIdAndMaterialIdAndColorId(
            Integer userId, Integer productId, Integer materialId, Integer colorId);

    Optional<CartItem> findByIdAndUserId(Integer id, Integer userId);
}