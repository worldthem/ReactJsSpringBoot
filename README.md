# ReactJsSpringBoot
ReactJs with Spring Boot login


# App Properties
bezkoder.app.jwtSecret= EWfsdaqewrGrsdFsd

bezkoder.app.jwtExpirationMs= 86400000

spring.jackson.mapper.accept_case_insensitive_properties=true


 @PostMapping("/signin") // Map ONLY POST Requests
    public ResponseEntity<?> login(@RequestBody LoginRequest user) {
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                 null));

      }
