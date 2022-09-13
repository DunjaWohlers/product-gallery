package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.BookmarkRepo;
import de.neuefische.cgnjava222.productgallery.UserRepo;
import de.neuefische.cgnjava222.productgallery.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ProductService productService;

    private final BookmarkRepo bookmarkRepo;

    public List<String> getAllNames() {
        return userRepo.findAll().stream().map(AppUser::username).toList();
    }

    public AppUser register(NewUser newUser) {
        return userRepo.save(new AppUser(
                newUser.username(),
                passwordEncoder.encode(newUser.password()),
                List.of("USER")));
    }

    public List<OrderDetailsItem> getMyBookmarks(String myName) {
        UserBookmarks userBookmarks = bookmarkRepo.findById(myName)
                .orElse(new UserBookmarks(myName, Collections.emptyList()));
        List<OrderItem> orderItems = userBookmarks.bookmarks();
        return orderItems.stream().map(orderItem -> new OrderDetailsItem(
                        productService.getDetailsOf(orderItem.productId())
                                .orElseThrow(() -> new NullPointerException(orderItem.productId())
                                ),
                        orderItem.count(),
                        orderItem.price()
                )
        ).toList();
    }

    public List<OrderItem> addBookmarks(Principal principal, List<OrderItem> bookmarks) {
        UserBookmarks userBookmarks = bookmarkRepo.save(new UserBookmarks(principal.getName(), bookmarks));
        return userBookmarks.bookmarks();
    }
}
