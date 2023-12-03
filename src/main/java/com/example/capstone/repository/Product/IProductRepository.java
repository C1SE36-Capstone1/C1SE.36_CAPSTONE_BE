package com.example.capstone.repository.Product;
import com.example.capstone.model.Product.Category;
import com.example.capstone.model.Product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByStatusTrue();

    List<Product> findByCategory(Category category);

    Product findByProductIdAndStatusTrue(Integer id);

    @Query(value = "select p.product_id, p.name, p.price, c.category_name " +
            "from products p " +
            "join categories c on p.category_id = c.category_id " +
            "where p.status = 1 and p.quantity > 0 " +
            "order by p.sold DESC ", nativeQuery = true)
    List<Product> findAllByCategoryAndSoldDesc(Category category);

    @Query(value = "select p.pet_id,p.name, null as price, p.status, p.images" +
            "from pet p" +
            "join breed br on br.breed_id = p.breed_id" +
            "where lower(br.breed_name) like lower(concat('%',:title,'%')) and p.status = true" +
            "union" +
            "select  pd.product_id,pd.name,  pd.price, pd.status, pd.image " +
            "from products pd" +
            "where lower(name) like lower(concat('%',:title,'%')) and pd.status = true;", nativeQuery = true)
    Page<Product> search(@Param("title") String title, Pageable pageable);
}
