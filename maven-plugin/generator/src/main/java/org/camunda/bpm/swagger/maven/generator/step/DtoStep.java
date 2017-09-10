package org.camunda.bpm.swagger.maven.generator.step;

import java.util.Optional;

import org.camunda.bpm.swagger.maven.generator.TypeHelper;
import org.camunda.bpm.swagger.maven.model.CamundaDto;
import org.camunda.bpm.swagger.maven.model.ModelRepository;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.JCodeModel;

import lombok.Getter;

public class DtoStep {

  @Getter
  private Optional<CamundaDto> camundaDto = Optional.empty();

  @Getter
  private final Class<?> baseClazz;
  private final ModelRepository modelRepository;

  private final JCodeModel owner;

  public DtoStep(final ModelRepository modelRepository, final Class<?> clazz, final JCodeModel owner) {
    this.modelRepository = modelRepository;
    this.baseClazz = clazz;
    this.owner = owner;
  }

  public boolean isDto() {
    return TypeHelper.isDto(baseClazz);
  }

  public AbstractJType getType(final JCodeModel jCodeModel) {
    if (isDto()) {
      return generateDto();
    } else {
      return jCodeModel._ref(baseClazz);
    }
  }

  private AbstractJClass generateDto() {
    final CamundaDto dto = new CamundaDto(modelRepository, baseClazz, owner);
    this.camundaDto = Optional.of(dto);
    return dto.getDefinedClass();
  }
}
