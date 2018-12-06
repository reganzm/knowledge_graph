package rdfox;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import uk.ac.ox.cs.JRDFox.JRDFoxException;
import uk.ac.ox.cs.JRDFox.Prefixes;
import uk.ac.ox.cs.JRDFox.store.DataStore;
import uk.ac.ox.cs.JRDFox.store.Resource;
import uk.ac.ox.cs.JRDFox.store.TupleIterator;

import java.io.File;

/**
 * RDFox使用教程
 */
public class RDFox_tutorial {
    public static void main(String[] args) throws Exception {
        // 从本体与实例数据文件创建各自的文件对象
        File ontologyFile = new File(RDFox_tutorial.class.getResource("/data/finance-onto.owl").toURI());
        File dataFile = new File(RDFox_tutorial.class.getResource("/data/finance-data.nt").toURI());

        // 自定义规则
        String userDefinedRules = "PREFIX p: <http://www.example.org/kse/finance#>"
                + "p:hold_share(?X, ?Y) :- p:control(?X, ?Y)."
                + "p:conn_trans(?Y, ?Z) :- p:hold_share(?X, ?Y), p:hold_share(?X, ?Z).";

        // 创建数据存储
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        DataStore store = new DataStore(DataStore.StoreType.ParallelSimpleNN);
        store.setNumberOfThreads(2);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);

        // 导入本体、实例数据与自定义规则
        store.importOntology(ontology);
        store.importFiles(new File[] {dataFile});
        store.importText(userDefinedRules);

        Prefixes prefixes = Prefixes.DEFAULT_IMMUTABLE_INSTANCE;
        // 创建SPARQL语句用于查询所有三元组
        TupleIterator tupleIterator = store.compileQuery("SELECT DISTINCT ?x ?y ?z WHERE{ ?x ?y ?z }", prefixes);
        System.out.println("Retrieving all triples before materialisation.");
        evaluateAndPrintResults(prefixes, tupleIterator);

        // 进行推理（Materialization + 规则推理）
        store.applyReasoning();

        System.out.println("Retrieving all triples after materialisation.");
        evaluateAndPrintResults(prefixes, tupleIterator);

        System.out.println("This is the end of the example!");
    }

    /**
     * 执行SPARQL语句并输出查询结果
     * @param prefixes
     * @param tupleIterator
     * @throws JRDFoxException
     */
    public static void evaluateAndPrintResults(Prefixes prefixes, TupleIterator tupleIterator) throws JRDFoxException {
        int numberOfRows = 0;
        System.out.println();
        System.out.println("=======================================================================================");
        int arity = tupleIterator.getArity();
        // We iterate trough the result tuples
        for (long multiplicity = tupleIterator.open(); multiplicity != 0; multiplicity = tupleIterator.advance()) {
            // We iterate trough the terms of each tuple
            for (int termIndex = 0; termIndex < arity; ++termIndex) {
                if (termIndex != 0)
                    System.out.print("  ");

                Resource resource = tupleIterator.getResource(termIndex);
                System.out.print(resource.toString(prefixes));
            }

            System.out.println();
            ++numberOfRows;
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("  The number of rows returned: " + numberOfRows);
        System.out.println("=======================================================================================");
        System.out.println();
    }
}
