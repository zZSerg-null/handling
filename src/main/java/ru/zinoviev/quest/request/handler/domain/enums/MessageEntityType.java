package ru.zinoviev.quest.request.handler.domain.enums;

public enum MessageEntityType {
    /**
     * - “bold” (bold text)
     * - “italic” (italic text)
     * - “underline” (underlined text)
     * - “strikethrough” (strikethrough text)
     * - “spoiler” (spoiler message)
     * - “blockquote” (block quotation)
     * - “code” (monowidth string)
     * - “pre” (monowidth block)
     * - “text_link” (for clickable text URLs)
     */
    BOLD, ITALIC, UNDERLINE, STRIKETHROUGH, SPOILER, BLOCKQUOTE, CODE, PRE, TEXT_LINK
}
