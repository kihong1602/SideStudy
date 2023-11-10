package com.blanc.spring.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

  private final ClassLoader classLoader;

  public MyAutoConfigImportSelector(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @NonNull
  @Override
  public String[] selectImports(@NonNull AnnotationMetadata importingClassMetadata) {
    List<String> autoConfigs = new ArrayList<>();
    ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);

    return autoConfigs.toArray(new String[0]);
//    Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
//    return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);
  }

}
