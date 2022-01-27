package com.mycompany.myapp.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mycompany.myapp.domain.Coche;
import com.mycompany.myapp.domain.Venta;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CocheSpecification extends JpaSpecificationExecutor<Coche> {

	public static Specification<Coche> searchingParam(String filter) {

		return new Specification<Coche>() {

			private static final long serialVersionUID = 1L;

			public Predicate toPredicate(Root<Coche> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				query.distinct(true);
				List<Predicate> ors = new ArrayList<Predicate>();

				Expression<String> marca = root.get("marca").as(String.class);
				Expression<String> modelo = root.get("modelo").as(String.class);
				Expression<String> color = root.get("color").as(String.class);
				Expression<String> numeroSerie = root.get("numeroSerie").as(String.class);

				//Join<Coche, Venta> venta = root.join("venta", JoinType.LEFT);

				// rojo ford focus
				String[] searchParam = filter.split(" ");
				for (int i = 0; i < searchParam.length; i++) {

					List<Predicate> predicates = new ArrayList<Predicate>();
					predicates.add(builder.like(marca, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(modelo, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(color, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(numeroSerie, "%" + searchParam[i] + "%"));

					//predicates.add(builder.like(venta.<String>get("tipoPago"), "%" + searchParam[i] + "%"));
					//predicates.add(builder.like(venta.<String>get("total"), "%" + searchParam[i] + "%"));


					ors.add(builder.or(predicates.toArray(new Predicate[] {})));
				}
				Predicate result = builder.and(ors.toArray(new Predicate[] {}));
				return result;
			}
		};
	}

}
