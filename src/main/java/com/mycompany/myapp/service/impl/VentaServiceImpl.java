package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Coche;
import com.mycompany.myapp.domain.Empleado;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.repository.CocheRepository;
import com.mycompany.myapp.repository.EmpleadoRepository;
import com.mycompany.myapp.repository.VentaRepository;
import com.mycompany.myapp.service.VentaService;
import com.mycompany.myapp.service.dto.VentaDTO;
import com.mycompany.myapp.service.mapper.VentaMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Venta}.
 */
@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private final Logger log = LoggerFactory.getLogger(VentaServiceImpl.class);

    private final VentaRepository ventaRepository;

    private final EmpleadoRepository empleadoRepository;

    private final CocheRepository cocheRepository;

    private final VentaMapper ventaMapper;

    public VentaServiceImpl(VentaRepository ventaRepository, VentaMapper ventaMapper, EmpleadoRepository empleadoRepository, CocheRepository cocheRepository) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.empleadoRepository = empleadoRepository;
        this.cocheRepository = cocheRepository;
    }

    @Override
    public VentaDTO save(VentaDTO ventaDTO) {
        log.debug("Request to save Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);

        venta.setFecha(LocalDate.now());

        venta = ventaRepository.save(venta);

        if(null != venta){

            Empleado empleado = venta.getEmpleado();
            Coche coche = venta.getCoches();

            if(null != coche){
                coche.setExposicion(false);
                cocheRepository.save(coche);

            }

            if(null == empleado.getNumeroVentas()){
                empleado.setNumeroVentas(0);
            }else{
                empleado.setNumeroVentas(empleado.getNumeroVentas()+1);
            }

            empleadoRepository.save(empleado);
        }

        return ventaMapper.toDto(venta);
    }

    @Override
    public VentaDTO update(VentaDTO ventaDTO) {
        log.debug("Request to save Venta : {}", ventaDTO);

        Venta venta = ventaMapper.toEntity(ventaDTO);
        Venta ventaAnterior = ventaRepository.getById(venta.getId());

        Empleado empleado = venta.getEmpleado();
        Empleado empleadoAnterior = ventaAnterior.getEmpleado();

        venta = ventaRepository.save(venta);

        if(null != venta){


            Coche coche = venta.getCoches();


            if(null != coche){
                coche.setExposicion(false);
                cocheRepository.save(coche);

            }

            if(null == empleado.getNumeroVentas()){
                empleado.setNumeroVentas(0);
            }else{

                if(!empleado.equals(empleadoAnterior)){

                    empleadoAnterior.setNumeroVentas(empleadoAnterior.getNumeroVentas()-1);
                    empleado.setNumeroVentas(empleado.getNumeroVentas()+1);
                }

            }

            empleadoRepository.save(empleado);
            empleadoRepository.save(empleadoAnterior);
        }

        return ventaMapper.toDto(venta);
    }

    @Override
    public Optional<VentaDTO> partialUpdate(VentaDTO ventaDTO) {
        log.debug("Request to partially update Venta : {}", ventaDTO);

        return ventaRepository
            .findById(ventaDTO.getId())
            .map(existingVenta -> {
                ventaMapper.partialUpdate(existingVenta, ventaDTO);

                return existingVenta;
            })
            .map(ventaRepository::save)
            .map(ventaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ventas");
        return ventaRepository.findAll(pageable).map(ventaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VentaDTO> findOne(Long id) {
        log.debug("Request to get Venta : {}", id);
        return ventaRepository.findById(id).map(ventaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
    }
}
