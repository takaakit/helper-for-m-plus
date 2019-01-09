package main.kotlin.service.kotlin
// ˅
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.ClassCollector
import main.kotlin.util.converter.KotlinClassConverter
// ˄

class ConvertToDataClass : IMyPluginActionDelegate {
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
                    || KotlinClassConverter(selectedElement).isKotlinClassKind(listOf(KotlinClassConverter.ClassKind.DATA))) {
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
            KotlinClassConverter(it as IClass).convertKotlinClassKind(listOf(KotlinClassConverter.ClassKind.DATA))
            println("Converted to data class :".plus(it.getFullName(".")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
