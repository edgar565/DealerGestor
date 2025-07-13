/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.entity.PartEntity;
import com.edgarsannic.dealergestor.domain.entity.RepairEntity;
import com.edgarsannic.dealergestor.domain.model.Part;
import com.edgarsannic.dealergestor.domain.repository.PartRepository;
import com.edgarsannic.dealergestor.domain.repository.RepairRepository;
import com.edgarsannic.dealergestor.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService{

    private final PartRepository partRepository;
    private final RepairRepository repairRepository;
    private final ModelMapperUtil modelMapperUtil;

    public PartServiceImpl(PartRepository partRepository, RepairRepository repairRepository, ModelMapperUtil modelMapperUtil) {
        this.partRepository = partRepository;
        this.repairRepository = repairRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<Part> findAllParts() {
        return partRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Part findPartById(Long id) {
        PartEntity partEntity = partRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found"));
        return modelMapperUtil.toModel(partEntity);
    }

    @Override
    public Part savePart(Part model) {
        RepairEntity repairEntity = repairRepository.findById(model.getRepair().getRepairId())
                .orElseThrow(() -> new RuntimeException("Repair not found"));


        PartEntity partEntity = new PartEntity();
        partEntity.setKeychain(model.getKeychain());
        partEntity.setNumberOrder(model.getNumberOrder());
        partEntity.setWork(model.getWork());
        partEntity.setMaterials(model.getMaterials());
        partEntity.setKeychain(model.getKeychain());
        partEntity.setRepairEntity(repairEntity);

        return modelMapperUtil.toModel(partRepository.save(partEntity));
    }

    @Override
    public Part updatePart(Long id, Part model) {
        PartEntity partEntity = partRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found"));

        RepairEntity repairEntity = repairRepository.findById(model.getRepair().getRepairId())
                .orElseThrow(() -> new RuntimeException("Repair not found"));

        partEntity.setKeychain(model.getKeychain());
        partEntity.setNumberOrder(model.getNumberOrder());
        partEntity.setWork(model.getWork());
        partEntity.setMaterials(model.getMaterials());
        partEntity.setObservations(model.getObservations());
        partEntity.setLights(model.isLights());
        partEntity.setBrakeSensors(model.isBrakeSensors());
        partEntity.setCableTension(model.isCableTension());
        partEntity.setTirePressure(model.isTirePressure());
        partEntity.setEngineOil(model.isEngineOil());
        partEntity.setWear(model.isWear());
        partEntity.setBrakeFluid(model.isBrakeFluid());
        partEntity.setBrakePads(model.isBrakePads());
        partEntity.setTransmissionKit(model.isTransmissionKit());
        partEntity.setSteeringCondition(model.isSteeringCondition());
        partEntity.setDynamicTest(model.isDynamicTest());

        partEntity.setDay1(model.getDay1());
        partEntity.setDay2(model.getDay2());
        partEntity.setDay3(model.getDay3());
        partEntity.setDay4(model.getDay4());
        partEntity.setDay5(model.getDay5());
        partEntity.setDay6(model.getDay6());

        partEntity.setTime1(model.getTime1());
        partEntity.setTime2(model.getTime2());
        partEntity.setTime3(model.getTime3());
        partEntity.setTime4(model.getTime4());
        partEntity.setTime5(model.getTime5());
        partEntity.setTime6(model.getTime6());
        partEntity.setTime7(model.getTime7());
        partEntity.setTime8(model.getTime8());
        partEntity.setTime9(model.getTime9());
        partEntity.setTime10(model.getTime10());
        partEntity.setTime11(model.getTime11());
        partEntity.setTime12(model.getTime12());
        partEntity.setKeychain(model.getKeychain());
        partEntity.setRepairEntity(repairEntity);

        return modelMapperUtil.toModel(partRepository.save(partEntity));
    }

    @Override
    public void deletePart(Long id) {
        partRepository.deleteById(id);
    }
}