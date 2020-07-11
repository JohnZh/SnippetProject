package com.john.myroutercompiler;

import com.google.auto.service.AutoService;
import com.john.jrouter.annotation.Constants;
import com.john.jrouter.annotation.Route;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by JohnZh on 2020/7/9
 *
 * <p></p>
 */
@AutoService(Processor.class)
public class JRouterProcessor extends AbstractProcessor {

    private static final String COMPILER_OPTIONS = "moduleName";

    private Filer mFiler;
    private Elements mElementUtils;
    private String mModuleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mModuleName = processingEnvironment.getOptions().get(COMPILER_OPTIONS); // from gradle javaCompileOptions.annotationProcessorOptions.arguments
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // JavaPoet ref: https://github.com/square/javapoet
        try {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Route.class);

            MethodSpec.Builder methodSpecBuilder = MethodSpec
                    .methodBuilder("onRegister")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class);

            ClassName className = ClassName.get("com.john.jrouter", "Register");

            if (elements.size() > 0) {
                for (Element element : elements) {
                    String path = element.getAnnotation(Route.class).value();
                    methodSpecBuilder.addStatement("$T.register($S, $T.class)", className, path, element);
                }
            }

            if (mModuleName == null || mModuleName.isEmpty()) {
                mModuleName = String.valueOf(System.currentTimeMillis());
            }

            ClassName superInterface = className.get("com.john.jrouter", "IRegister");
            TypeSpec typeSpec = TypeSpec
                    .classBuilder("RouteHelper_" + mModuleName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodSpecBuilder.build())
                    .addSuperinterface(superInterface)
                    .build();

            JavaFile javaFile = JavaFile.builder(Constants.packageName, typeSpec).build();

            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Route.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    @Override
    public Set<String> getSupportedOptions() {
        Set<String> set = new HashSet<>();
        set.add(COMPILER_OPTIONS);
        return set;
    }
}
