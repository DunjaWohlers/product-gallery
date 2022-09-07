package de.neuefische.cgnjava222.productgallery;

import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.http44.api.Response;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithAnonymousUser
    void getProducts() throws Exception {
        mockMvc.perform(get("/api/product/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Autowired
    ProductRepo productRepo;


    @MockBean
    private Uploader uploader;

    @MockBean
    private Api api;

    @MockBean
    private Cloudinary cloudinary;

    @Test
    @WithAnonymousUser
    void getProductPerId() throws Exception {
        NewProduct newProduct = new NewProduct("ABC", "def", List.of(new ImageInfo("asd", "asd")), 4, 5);
        String id = UUID.randomUUID().toString();
        Product product = Product.ProductFactory.create(id, newProduct);
        productRepo.save(product);

        String content = mockMvc.perform(
                        get("/api/product/details/" + id).with(csrf())
                )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Product actualProduct = objectMapper.readValue(content, Product.class);

        Assertions.assertEquals(actualProduct, product);
    }

    @Test
    void getOneProductNotExisting() throws Exception {
        String notExistingID = "1a";

        mockMvc.perform(
                        get("/api/product/details/" + notExistingID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isNotFound()).andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException)).andExpect(result -> {
                    if (result.getResolvedException() == null) {
                        fail();
                    } else {
                        assertEquals("Product not Found (id: 1a )", result.getResolvedException().getMessage());
                    }
                });
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void addProducts() throws Exception {
        mockMvc.perform(
                        post("/api/product").contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                            {
                                                   "title": "Brett",
                                                   "description": "Zum Frühstücken oder sonstiger Verwendung",
                                                   "pictureObj": [
                                                        {
                                                           "url": "http://res.cloudinary.com/dcnqizhmg/image/upload/v1661501086/equaeqbgdxv9mkfczq1i.jpg",
                                                           "publicId": "equaeqbgdxv9mkfczq1i"
                                                        }
                                                    ],
                                                    "price": 5,
                                                    "availableCount": 4
                                                }
                                        """)
                                .with(csrf())
                )
                .andExpect(status().is(201))
                .andExpect(content().json("""
                                {
                                     "title": "Brett",
                                     "description": "Zum Frühstücken oder sonstiger Verwendung",
                                     "pictureObj": [
                                        {
                                         "url": "http://res.cloudinary.com/dcnqizhmg/image/upload/v1661501086/equaeqbgdxv9mkfczq1i.jpg",
                                         "publicId": "equaeqbgdxv9mkfczq1i"
                                         }
                                      ],
                                      "price": 5,
                                      "availableCount": 4
                                 }
                        """));
    }

    @Test
    @WithAnonymousUser
    void tryToAddProductAsAnonymousUser() throws Exception {
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                           "title": "Brett",
                                           "description": "Zum Frühstücken oder sonstiger Verwendung",
                                           "pictureObj": [
                                                {
                                                   "url": "http://res.cloudinary.com/dcnqizhmg/image/upload/v1661501086/equaeqbgdxv9mkfczq1i.jpg",
                                                   "publicId": "equaeqbgdxv9mkfczq1i"
                                                }
                                            ],
                                            "price": 5,
                                            "availableCount": 4
                                        }
                                """)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void deleteExistingProduct() throws Exception {
        String addPromise = mockMvc.perform(
                post("/api/product/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                         {
                                             "id": "97a11b9f-505b-4f49-90f4-308a0f5b0bc0",
                                             "title": "Brett",
                                             "description": "Zum Frühstücken oder sonstiger Verwendung",
                                             "pictureObj": [
                                                {
                                                 "url": "http://res.cloudinary.com/dcnqizhmg/image/upload/v1661501086/equaeqbgdxv9mkfczq1i.jpg",
                                                 "publicId": "equaeqbgdxv9mkfczq1i"
                                                 }
                                              ],
                                              "price": 5,
                                              "availableCount": 4
                                         }
                                """)
        ).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Product addedProductResult = objectMapper.readValue(addPromise, Product.class);

        when(cloudinary.api()).thenReturn(api);
        when(api.deleteResources(
                List.of("equaeqbgdxv9mkfczq1i"), Map.of()))
                .thenReturn(
                        new Response(
                                new BasicHttpResponse(
                                        new BasicStatusLine(
                                                new HttpVersion(3, 4), 4, "bl"
                                        )
                                ),
                                Map.of("url", "bla://blub", "public_id", "bma")
                        )
                );

        String id = addedProductResult.id();
        mockMvc.perform(
                        delete("/api/product/" + id).with(csrf())
                )
                .andExpect(status().is(204)).andReturn().getResponse();
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void deleteNotExistingProduct() throws Exception {
        String notExistingID = "ABC123";
        mockMvc.perform(
                        delete("/api/product/" + notExistingID).with(csrf())
                )
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void updateProduct() throws Exception {
        String saveResult = mockMvc.perform(post("/api/product/").contentType(MediaType.APPLICATION_JSON).content("""
                {
                             "title": "Brett",
                             "description": "Zum Frühstücken oder sonstiger Verwendung",
                             "pictureObj": [
                                {
                                 "url": "http://res.cloudinary.com/dcnqizhmg/image/upload/v1661501086/equaeqbgdxv9mkfczq1i.jpg",
                                 "public_id": "equaeqbgdxv9mkfczq1i"
                                 }
                              ],
                              "price": 5,
                              "availableCount": 4
                         }
                """).with(csrf())).andExpect(status().is(201)).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product saveResultProduct = objectMapper.readValue(saveResult, Product.class);

        NewProduct newProduct = new NewProduct("Product1", "1a Qualität", List.of(new ImageInfo("http://www.bla.de", "PublicID7")), 5, 6);
        Product expectedProduct = Product.ProductFactory.create(saveResultProduct.id(), newProduct);
        String updateResponse = mockMvc.perform(
                        put("/api/product/" + saveResultProduct.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(expectedProduct)).with(csrf())
                )
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualProduct = objectMapper.readValue(updateResponse, Product.class);
        Assertions.assertEquals(saveResultProduct.id(), actualProduct.id());
        Assertions.assertEquals(expectedProduct, actualProduct);
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void deleteImageFromExistingProduct() throws Exception {
        //Save on DB:
        String saveResult = mockMvc.perform(
                        post("/api/product/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                                     "title": "Brett",
                                                     "description": "Zum Frühstücken oder sonstiger Verwendung",
                                                     "pictureObj": [
                                                        {
                                                         "url": "http://res.cloudinary.com/dcnqizhmg/image/upload/v1661501086/equaeqbgdxv9mkfczq1i.jpg",
                                                         "publicId": "XYZ"
                                                         }
                                                      ],
                                                      "price": 5,
                                                      "availableCount": 4
                                                 }
                                        """).with(csrf())
                )
                .andExpect(status().is(201))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product saveResultProduct = objectMapper.readValue(saveResult, Product.class);
        String saveResultId = saveResultProduct.id();
        String filePublicId = "XYZ";
        when(cloudinary.api()).thenReturn(api);
        when(api.deleteResources(
                List.of(filePublicId), Map.of()))
                .thenReturn(
                        new Response(
                                new BasicHttpResponse(
                                        new BasicStatusLine(
                                                new HttpVersion(3, 4), 4, "bl"
                                        )
                                ),
                                Map.of("url", "bla://blub", "public_id", filePublicId)
                        )
                );

        //delete
        mockMvc.perform(
                delete("/api/product/" + saveResultId + "/" + filePublicId).with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void deleteImageFromProductWithIdNotfound() throws Exception {
        String saveResultId = "BB";
        Map fileUploadReturn = Map.of("url", "bla", "publicId", "X");
        mockMvc.perform(
                        delete("/api/product/" + saveResultId + "/" + fileUploadReturn.get("publicId")
                        ).with(csrf()
                        )
                )
                .andExpect(
                        status().is(404)
                ).andExpect(
                        result ->
                                assertTrue(
                                        result.getResolvedException() instanceof ProductNotFoundException)
                ).andExpect(result -> {
                    if (result.getResolvedException() == null) {
                        fail();
                    } else {
                        assertEquals("Product not Found (id: BB )", result.getResolvedException().getMessage());
                    }
                })
        ;
    }


    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void deleteImageFromExistingProductWithFileNotFoundException() throws Exception {
        NewProduct newProduct = new NewProduct("ABC", "def", List.of(new ImageInfo("asd", "asd")), 4, 5);
        String id = UUID.randomUUID().toString();
        Product product = Product.ProductFactory.create(id, newProduct);
        productRepo.save(product);

        String publicId = "MICHGIBTSNICH";

        when(cloudinary.api()).thenReturn(api);
        //when(api
        //        .deleteResources(
        //                anyList(),
        //                anyMap()
        //        )).thenReturn(Map.of("abc", "bla", "Blub", "bli"));

        mockMvc.perform(
                delete("/api/product/" + id + "/" + publicId)
                        .with(csrf())


                //  ).andExpect(
                //          result ->
                //                  assertTrue(
                //                          result.getResolvedException() instanceof FileNotDeletedException)
                //  ).andExpect(
                //          result -> {
                //              if (result.getResolvedException() == null) {
                //                  fail();
                //              } else {
                //                  assertEquals("Löschen der Datei: " + publicId + " fehlgeschlagen ", result.getResolvedException().getMessage());
                //              }
                //          }
        );
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void deleteImageFromProductWithIdTESTTEST() throws Exception {
        //Save on DB:
        String saveResult = mockMvc.perform(
                        post("/api/product/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "Brett",
                                            "description": "Zum Frühstücken oder sonstiger Verwendung",
                                            "pictureObj": [
                                               {
                                                "url": "http://res.cloudinary.com/dcnqizhmg/image/upload/v1661501086/equaeqbgdxv9mkfczq1i.jpg",
                                                "publicId": "XYZ"
                                                }
                                             ],
                                             "price": 5,
                                             "availableCount": 4
                                        }
                                         """).with(csrf())
                )
                .andExpect(status().is(201))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product saveResultProduct = objectMapper.readValue(saveResult, Product.class);
        String saveResultId = saveResultProduct.id();

        //save File:
        File file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("sawIcon.png")).getFile());

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(file, ObjectUtils.emptyMap())).thenReturn(
                Map.of("url", "http://res.cloudinary.com/dcnqizhmg/image/upload/v1661501086/equaeqbgdxv9mkfczq1i.jpg",
                        "publicId", "XYZ"));
        Map fileUploadReturn = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

        //delete:

        ApiResponse abc = cloudinary.api().deleteResources(List.of("abc"), Map.of("ab", "cd"));

        mockMvc.perform(
                delete("/api/product/" + saveResultId + "/" + "ABC").with(csrf())
        ).andExpect(status().isNotFound());
    }
}
