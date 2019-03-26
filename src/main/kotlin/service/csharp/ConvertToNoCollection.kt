package main.kotlin.service.csharp
// ˅
import com.change_vision.jude.api.inf.model.IAssociation
import com.change_vision.jude.api.inf.model.INamedElement
import java.util.*
import java.text.*
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AssociationCollector
import main.kotlin.util.converter.CsharpAssociationConverter

// ˄

class ConvertToNoCollection : IMyPluginActionDelegate {
    // ˅
    
    // ˄

    override fun collectSelectedElements(): List<INamedElement> {
        // ˅
        return AssociationCollector().collectFromSelectedElements()
        // ˄
    }

    override fun excludeSetElements(selectedElements: List<INamedElement>): List<INamedElement> {
        // ˅
        val targetAssociations = mutableListOf<INamedElement>()

        for (selectedElement in selectedElements) {
            if (selectedElement !is IAssociation
                    || CsharpAssociationConverter(selectedElement).isCsharpCollectionKind(listOf(CsharpAssociationConverter.CollectionKind.NO_COLLECTION))) {
                continue
            }
            targetAssociations.add(selectedElement)
        }

        return targetAssociations
        // ˄
    }

    override fun editElements(elements: List<INamedElement>) {
        // ˅
        elements.forEach {
            CsharpAssociationConverter(it as IAssociation).convertCsharpCollectionKind(listOf(CsharpAssociationConverter.CollectionKind.NO_COLLECTION))
            if (it.memberEnds[0].navigability == "Navigable") {
                println("Converted to Dictionary :".plus(it.memberEnds[0].getFullName(".")))
            } else {
                println("Converted to Dictionary :".plus(it.memberEnds[1].getFullName(".")))
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
