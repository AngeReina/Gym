package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query(value = """
        SELECT p.*
        FROM plan p
    """, nativeQuery = true)
    List<Plan> listarTodos();

    @Query(value = """
        SELECT p.*
        FROM plan p
        ORDER BY p.costo ASC
    """, nativeQuery = true)
    List<Plan> ordenarPorPrecio();

    @Query(value = """
        SELECT p.*
        FROM plan p
        WHERE p.duracion_dias >= :dias
    """, nativeQuery = true)
    List<Plan> planesLargos(@Param("dias") Integer dias);

    @Query(value = """
        SELECT COUNT('X')
        FROM plan p
    """, nativeQuery = true)
    Long contarPlanes();

    @Query(value = """
        SELECT MAX(p.costo)
        FROM plan p
    """, nativeQuery = true)
    BigDecimal planMasCostoso();

    @Query(value = """
        SELECT p.id_plan, p.nombre_plan, COUNT(s.id_suscripcion) AS total_comprados
        FROM plan p
        JOIN suscripcion s ON s.id_plan = p.id_plan
        GROUP BY p.id_plan, p.nombre_plan
        ORDER BY total_comprados DESC
    """, nativeQuery = true)
    List<Object[]> planesMasComprados();

    @Query(value = """
        SELECT p.id_plan, p.nombre_plan, SUM(f.total_factura) AS ingresos
        FROM plan p
        JOIN suscripcion s ON s.id_plan = p.id_plan
        JOIN factura f ON f.id_suscripcion = s.id_suscripcion
        WHERE f.estado = 'PAGADA'
        GROUP BY p.id_plan, p.nombre_plan
        ORDER BY ingresos DESC
    """, nativeQuery = true)
    List<Object[]> ingresosPorPlan();

    @Query(value = """
        SELECT p.id_plan, p.nombre_plan, COUNT(s.id_suscripcion) AS activos
        FROM plan p
        JOIN suscripcion s ON s.id_plan = p.id_plan
        WHERE s.estado = 'ACTIVO'
        GROUP BY p.id_plan, p.nombre_plan
        ORDER BY activos DESC
    """, nativeQuery = true)
    List<Object[]> planesActivos();
}