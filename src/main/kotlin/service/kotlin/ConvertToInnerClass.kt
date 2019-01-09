package main.kotlin.service.kotlin
// ˅
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.ClassCollector
import main.kotlin.util.converter.KotlinClassConverter
// ˄

class ConvertToInnerClass : IMyPluginActionDelegate {
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
                    || selectedElement.owner !is IClass) {
                continue
            } else if (KotlinClassConverter(selectedElement).isKotlinClassKind(listOf(KotlinClassConverter.ClassKind.INNER))) {
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
            KotlinClassConverter(it as IClass).convertKotlinClassKind(listOf(KotlinClassConverter.ClassKind.INNER))
            println("Converted to inner class :".plus(it.getFullName(".")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
