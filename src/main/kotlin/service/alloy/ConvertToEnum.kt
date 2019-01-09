package main.kotlin.service.alloy
// ˅
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.ClassCollector
import main.kotlin.util.converter.AlloyClassConverter
// ˄

class ConvertToEnum : IMyPluginActionDelegate {
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

        for (selectElement in selectedElements) {
            if (selectElement is IClass
                    && AlloyClassConverter(selectElement).isAlloyClassKind(listOf(AlloyClassConverter.ClassKind.ENUM))) {
                continue
            }
            targetClasses.add(selectElement)
        }

        return targetClasses
        // ˄
    }

    override fun editElements(elements: List<INamedElement>) {
        // ˅
        elements.forEach {
            AlloyClassConverter(it as IClass).convertAlloyClassKind(listOf(AlloyClassConverter.ClassKind.ENUM))
            println("Converted to enum :".plus(it.getFullName(".")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
