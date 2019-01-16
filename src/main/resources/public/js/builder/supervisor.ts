class Supervisor {
    builder: TableBuilder;

    constructor(Builder: TableBuilder) {
        this.builder = Builder;
    }

    build() {
        this.builder.buildHeader();
        this.builder.buildContent();
        this.builder.buildFooter();
    }
}