package main.kotlin.service.cpp
// ˅
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.ClassCollector
import main.kotlin.util.converter.CppClassConverter
// ˄

class ConvertToStruct : IMyPluginActionDelegate {
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
                    || CppClassConverter(selectedElement).isCppClassKind(listOf(CppClassConverter.ClassKind.STRUCT))) {
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
            CppClassConverter(it as IClass).convertCppClassKind(listOf(CppClassConverter.ClassKind.STRUCT))
            println("Converted to struct :".plus(it.getFullName(".")))
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
