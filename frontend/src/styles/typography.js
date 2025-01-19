const typography = (palette) => ({
  fontFamily: "'Roboto', sans-serif", // Font principale per il body
  h1: {
    fontFamily: "'Playfair Display', serif", // Font per il titolo h1
    fontWeight: 700, // Peso del font per h1
    fontSize: "4rem", // Dimensione del font per h1
    color: palette.main.primary, // Colore del titolo h1
  },
  h2: {
    fontFamily: "'Playfair Display', serif", // Font per il titolo h2
    fontWeight: 600, // Peso del font per h2
    fontSize: "2.5rem", // Dimensione del font per h2
    color: palette.main.primary, // Colore del titolo h2
  },
  h3: {
    fontFamily: "'Playfair Display', serif",
    fontWeight: 600,
    fontSize: "2rem",
    color: palette.main.primary,
  },
  h4: {
    fontFamily: "'Playfair Display', serif",
    fontWeight: 600,
    fontSize: "1.7rem", // Dimensione del font per h4
    color: palette.main.black, // Colore del titolo h4
  },
  h5: {
    fontFamily: "'Playfair Display', serif",
    fontWeight: 600,
    fontSize: "1.5rem", // Dimensione del font per h5
    color: palette.main.black, // Colore del titolo h5
  },
  h6: {
    fontFamily: "'Playfair Display', serif",
    fontWeight: 600,
    fontSize: "1.25rem", // Dimensione del font per h6
    color: palette.main.primary, // Colore del titolo h6
  },
  bodyBold: {
    fontFamily: "'Roboto', sans-serif",
    fontSize: "1.2rem",
    fontWeight: 700,
  },
  body: {
    fontFamily: "'Roboto', sans-serif",
    fontSize: "1rem",
  },
  bodyXsBold: {
    fontFamily: "'Roboto', sans-serif",
    fontSize: "0.875rem",
    fontWeight: 700,
  },
  bodyXs: {
    fontFamily: "'Roboto', sans-serif", // Font per il corpo
    fontSize: "0.875rem", // Dimensione del font per il corpo più piccolo
  },
});

export default typography;
