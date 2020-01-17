package uk.gov.gchq.gaffer.federatedstore;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import uk.gov.gchq.gaffer.accumulostore.MockAccumuloStore;
import uk.gov.gchq.gaffer.graph.GraphConfig;
import uk.gov.gchq.gaffer.graph.GraphSerialisable;
import uk.gov.gchq.gaffer.store.StoreProperties;
import uk.gov.gchq.gaffer.store.schema.Schema;
import uk.gov.gchq.gaffer.user.User;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AdminGetAllGraphInfoTest {

    public static final String ADMIN_AUTH = "AdminAuth";
    private FederatedAccess access;
    private FederatedStore store;
    private User adminUser;
    private StoreProperties properties;

    @Before
    public void setUp() throws Exception {
        access = new FederatedAccess(Sets.newHashSet("authA"), "testuser1", false, FederatedGraphStorage.DEFAULT_DISABLED_BY_DEFAULT);
        store = new FederatedStore();
        final StoreProperties fedProps = new StoreProperties();
        fedProps.set(StoreProperties.ADMIN_AUTH, ADMIN_AUTH);
        store.initialise("testFedStore", null, fedProps);
        adminUser = new User("adminUser", null, Sets.newHashSet(ADMIN_AUTH));
        this.properties = new StoreProperties();
        this.properties.setStoreClass(MockAccumuloStore.class);
    }

    @Test
    public void shouldGetAllGraphsAndAuthsAsAdmin() throws Exception {
        final String graph1 = "graph1";

        store.addGraphs(access, new GraphSerialisable.Builder()
                .config(new GraphConfig.Builder()
                        .graphId(graph1)
                        .build())
                .schema(new Schema())
                .properties(properties)
                .build());

        final HashMap<String, Object> allGraphsAndAuths = store.getAllGraphsAndAuthsAsAdmin(adminUser);

        assertNotNull(allGraphsAndAuths);
        assertFalse(allGraphsAndAuths.isEmpty());
        assertEquals(graph1, allGraphsAndAuths.keySet().toArray(new String[]{})[0]);
    }

    @Test
    public void shouldNotGetAllGraphsAndAuthsAsAdmin() throws Exception {
        final String graph1 = "graph1";

        store.addGraphs(access, new GraphSerialisable.Builder()
                .config(new GraphConfig.Builder()
                        .graphId(graph1)
                        .build())
                .schema(new Schema())
                .properties(properties)
                .build());

        final HashMap<String, Object> allGraphsAndAuths = store.getAllGraphsAndAuthsAsAdmin(new User());

        assertNotNull(allGraphsAndAuths);
        assertTrue(allGraphsAndAuths.isEmpty());
    }
}
