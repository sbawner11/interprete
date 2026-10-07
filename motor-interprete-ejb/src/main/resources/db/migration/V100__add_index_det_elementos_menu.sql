-- Índice para búsquedas por descripción
CREATE INDEX idx_det_elementos_menu_descripcion_lower 
ON motor_interprete.det_elementos_menu(LOWER(descripcion_elemento));

-- Índice compuesto para filtrado 
CREATE INDEX idx_det_elementos_menu_componente_activo 
ON motor_interprete.det_elementos_menu(id_componente_menu, activo, descripcion_elemento);