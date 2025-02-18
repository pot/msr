package dev.nateweisz.msr;

import com.google.auto.service.AutoService;
import com.palantir.javapoet.*;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
import net.minestom.server.utils.NamespaceID;

import javax.annotation.processing.*;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedAnnotationTypes("dev.nateweisz.msr.MSRHandler")
public class MSRProcessor extends AbstractProcessor {
    private final List<PlacementRuleData> rules = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        System.out.println("MSRProcessor initialized");
        System.out.println("Supported annotations: " + getSupportedAnnotationTypes());
        System.out.println("Processing environment: " + processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("========== MSRProcessor applying to project ==========");
        TypeElement msrHandler = processingEnv.getElementUtils().getTypeElement(MSRHandler.class.getName());
        for (var element : roundEnv.getElementsAnnotatedWith(msrHandler)) {
            var typeElement = (TypeElement) element;
            var annotation = element.getAnnotation(MSRHandler.class);
            var predicate = annotation.predicate();

            List<Block> blocks = new ArrayList<>();
            for (var block : Block.values()) {
                if (predicate.isEmpty() || block.name().matches(predicate)) {
                    blocks.add(block);
                }
            }



            try {
                String className = typeElement.getQualifiedName().toString();

                System.out.println("==== PROCESSING " + className + " ======");

                for (var block : blocks) {
                    rules.add(new PlacementRuleData(className, block));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (roundEnv.processingOver() && !rules.isEmpty()) {
            List<FieldSpec> fields = new ArrayList<>();
            for (var rule : rules) {
                fields.add(FieldSpec.builder(BlockPlacementRule.class, convertToFieldName(rule.block().namespace()))
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("new $T($T.fromNamespaceId($S))",
                                ClassName.bestGuess(rule.ruleClass()),
                                Block.class,
                                rule.block().name())
                        .build());
            }

            MethodSpec getAll = MethodSpec.methodBuilder("getAll")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(List.class)
                    .addStatement("return List.of($L)",
                            rules.stream()
                                    .map(rule -> convertToFieldName(rule.block().namespace()))
                                    .collect(Collectors.joining(", ")))

                    .build();

            MethodSpec register = MethodSpec.methodBuilder("register")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addParameter(BlockPlacementRule[].class, "rules")
                    .addStatement("for ($T rule : rules) $T.getBlockManager().registerBlockPlacementRule(rule)",
                            BlockPlacementRule.class,
                            MinecraftServer.class)
                    .build();

            MethodSpec registerAll = MethodSpec.methodBuilder("registerAll")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addStatement("register(new $T[]{$L})",
                            BlockPlacementRule.class,
                            rules.stream()
                                .map(rule -> convertToFieldName(rule.block().namespace()))
                                .collect(Collectors.joining(", ")))
                    .build();

            TypeSpec handlerClass = TypeSpec.classBuilder("MSRPlacementRules")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addFields(fields)
                    .addMethods(List.of(getAll, register, registerAll))
                    .build();

            JavaFile javaFile = JavaFile.builder("dev.nateweisz.msr", handlerClass)
                    //.addStaticImport(Block.class)
                    //.addStaticImport(BlockPlacementRule.class)
                    .build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private String convertToFieldName(NamespaceID namespace) {
        return namespace.path().toUpperCase();
    }
}
