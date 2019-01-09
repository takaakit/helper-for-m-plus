package main.kotlin.service.kotlin
// ˅
import com.change_vision.jude.api.inf.model.IAssociation
import com.change_vision.jude.api.inf.model.INamedElement
import main.kotlin.service.IMyPluginActionDelegate
import main.kotlin.util.collector.AssociationCollector
import main.kotlin.util.converter.KotlinAssociationConverter
// ˄

class ConvertToList : IMyPluginActionDelegate {
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
                    || KotlinAssociationConverter(selectedElement).isKotlinCollectionKind(listOf(KotlinAssociationConverter.CollectionKind.LIST))) {
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
            KotlinAssociationConverter(it as IAssociation).convertKotlinCollectionKind(listOf(KotlinAssociationConverter.CollectionKind.LIST))
            if (it.memberEnds.get(0).navigability == "Navigable") {
                println("Converted to List :".plus(it.memberEnds[0].getFullName(".")))
            } else {
                println("Converted to List :".plus(it.memberEnds[1].getFullName(".")))
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
