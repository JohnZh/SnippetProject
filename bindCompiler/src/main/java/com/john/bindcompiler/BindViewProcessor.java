package com.john.bindcompiler;

import com.john.bindlib.annotation.BindView;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

public class BindViewProcessor extends AbstractProcessor {

    private static class ViewInfo {
        public ViewInfo(int id, String viewName) {
            this.id = id;
            this.viewName = viewName;
        }

        public int id;
        public String viewName;

        @Override
        public boolean equals(Object o) {
            return this.viewName.equals(((ViewInfo) o).viewName);
        }
    }

    private Map<TypeElement, Set<ViewInfo>> mMap = new HashMap<>();
    private Filer mFiler;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(BindView.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : elements) {
            if (element instanceof VariableElement) {
                Element enclosingElement = element.getEnclosingElement();
                if (enclosingElement instanceof TypeElement) {
                    Set<ViewInfo> viewInfoSet = mMap.get(enclosingElement);
                    if (viewInfoSet == null) {
                        viewInfoSet = new HashSet<>();
                    }
                    BindView annotation = element.getAnnotation(BindView.class);
                    String viewName = element.getSimpleName().toString();
                    viewInfoSet.add(new ViewInfo(annotation.value(), viewName));
                    mMap.put((TypeElement) enclosingElement, viewInfoSet);
                }
            }
        }

        Set<TypeElement> typeElementSet = mMap.keySet();
        for (TypeElement element : typeElementSet) {
            Set<ViewInfo> viewInfoSet = mMap.get(element);
            String code = generateUtilClassCode(element, viewInfoSet);
            String utilClassName = element.getSimpleName().toString() + "_Binder";
            try {
                JavaFileObject sourceFile = mFiler.createSourceFile(utilClassName, element);
                Writer writer = sourceFile.openWriter();
                writer.write(code);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private String generateUtilClassCode(TypeElement element, Set<ViewInfo> viewInfoSet) {
        String className = element.getSimpleName().toString();
        PackageElement packageOf = mElementUtils.getPackageOf(element);
        String packageName = packageOf.getQualifiedName().toString();
        String utilClassName = className + "_Binder";

        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";").append("\n")
                .append("import com.john.bindlib.IBinder;").append("\n")
                .append("public class ").append(utilClassName).append(" implements IBinder ").append("{")
                .append("\n")
                .append("\t").append("@Override").append("\n")
                .append("\t").append("public void bind(Object o) {").append("\n")
                .append("\t\t").append(className).append(" act = (").append(className).append(") o;")
                .append("\n");
        for (ViewInfo viewInfo : viewInfoSet) {
            builder.append("\t\t").append("act.").append(viewInfo.viewName).append(" = ")
                    .append("act.findViewById(").append(viewInfo.id).append(");").append("\n");
        }
        builder.append("\t}").append("\n")
                .append("}");
        return builder.toString();
    }
}
