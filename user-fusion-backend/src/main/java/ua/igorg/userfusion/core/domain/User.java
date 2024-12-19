package ua.igorg.userfusion.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/** Created by igorg on 31.05.2024 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User {
  private String id;
  private String username;
  private String name;
  private String surname;
}
