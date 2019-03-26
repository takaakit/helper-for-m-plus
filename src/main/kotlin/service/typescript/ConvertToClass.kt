package main.kotlin.service.typescript
// ˅
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import java.util.*
import java.text.*
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.ClassCollector
import main.kotlin.util.converter.TypescriptClassConverter

// ˄

class ConvertToClass : IMyPluginActionDelegate {
    // ˅
    
    // ˄

    override fun collectSelectedElements(): List<INamedElement> {
        // ˅
        return ClassCollector().collectFromSelectedElements()
        // ˄
    }

    override fun excludeSetElements(selectedElements: List<INamedElement>): List<INamedElement> {
        // ˅
        val targetClasses = mutableListOf<INamedElement>()

        for (selectedElement in selectedElements) {
            if (selectedElement !is IClass
                    || TypescriptClassConverter(selectedElement).isTypescriptClassKind(listOf(TypescriptClassConverter.ClassKind.CLASS))) {
                continue
            }
            targetClasses.add(selectedElement)
        }

        return targetClasses
        // ˄
    }

    override fun editElements(elements: List<INamedElement>) {
        // ˅
        elements.forEach {
            TypescriptClassConverter(it as IClass).convertTypescriptClassKind(listOf(TypescriptClassConverter.ClassKind.CLASS))
            println("Converted to class :".plus(it.getFullName(".")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
